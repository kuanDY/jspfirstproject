package com.playdata.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/register")

public class registerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String hashedpassword = LoginServlet.hashpassword(password);

        if(isAvailableUsername(req,username)){
            //username이 사용 중이지 않아서 가입이 가능한 경우
            int resultRowCount = registerUser(username, hashedpassword);

            if(resultRowCount == 1){
                //가입 성공
                HttpSession session = req.getSession();
                session. setAttribute("username",username);

                //메인페이지로 이동
                resp.sendRedirect("/index.jsp");
            } else {
                //가입 실패
                //회원 가입 페이지로 이동.
                resp.sendRedirect("/register.jsp");
            }
        }

    }

    /**
     * 데이터베이스에 사용자 정보(username, hashedPassword)를
     * @param username
     * @param hashedpassword
     * @return
     */

    private int registerUser(String username, String hashedpassword) {
        String sql ="INSERT INTO users(username, password) VALUES(?, ?)";
        Connection conn =(Connection) getServletContext().getAttribute("conn");

        try (PreparedStatement pstmt= conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2,hashedpassword);

            return pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 데이터베이스에 사용자 정보(username)가 이미 사용 중인지 조회
     * @param req
     * @param username
     * @return 이미 사용 중인 경우 false, 사용 가능한 경우 true
     */
    private boolean isAvailableUsername(HttpServletRequest req ,String username) {
        // DB에서 username이 존재하는지 확인
        String sql = "SELECT * FROM users WHERE username = ?";
        Connection conn = (Connection)req.getServletContext().getAttribute("conn");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,username);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    //username이 존재하는 경우
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return true;
    }


}

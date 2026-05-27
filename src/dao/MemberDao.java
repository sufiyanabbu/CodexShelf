package dao;

import model.Member;
import util.DBConnection;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MemberDao {
    public void addMember(Member member){
        String Query = "INSERT INTO member (member_id, name, email, phone, join_Date, is_Active) VALUES (?, ?, ?, ?, ?, ?) ";
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.setInt(1, member.getMemberId());
            ps.setString(2, member.getName());
            ps.setString(3, member.getEmail());
            ps.setString(4, member.getPhone());
            ps.setDate(5, Date.valueOf(member.getJoinDate()));
            ps.setBoolean(6, member.getIsActive());
            ps.executeUpdate();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Member> getAllMembers(){
        String Query = " SELECT * FROM member";
        List<Member> members = new ArrayList<>();
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(Query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getInt("member_id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPhone(rs.getString("phone"));
                member.setJoinDate(rs.getDate("join_date").toLocalDate());
                member.setIsActive(rs.getBoolean("is_Active"));
                members.add(member);
            }
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }
    public void deleteMember(int memberid){
        String Query = "DELETE FROM member WHERE member_id = ?";
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.setInt(1, memberid);
            ps.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateMember(Member member){
        String Query = "UPDATE member SET name=?, emial=?, phone=?, joinDate=?, isActive=? WHERE member_id=?";
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPhone());
            ps.setDate(5, Date.valueOf(member.getJoinDate()));
            ps.setBoolean(5, member.getIsActive());
            ps.executeUpdate();
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public Member getMemberById(int memberid){
        String Query = "SELECT * FROM member WHERE member_id = ?";
        Member member = null;
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.setInt(1, memberid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                member = new Member();
                member.setMemberId(rs.getInt("member_id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPhone(rs.getString("phone"));
                member.setIsActive(rs.getBoolean("is_Active"));
                member.setJoinDate(rs.getDate("join_date").toLocalDate());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return member;
    }
}

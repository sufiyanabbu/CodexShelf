package service;

import dao.MemberDao;
import model.Member;
import java.time.LocalDate;
import java.util.List;

public class MemberService {

    private MemberDao memberDAO = new MemberDao();

    public void registerMember(Member member) {
        // business rule — name and email can't be empty
        if (member.getName() == null || member.getName().trim().isEmpty()) {
            System.out.println("Error: member name cannot be empty.");
            return;
        }
        if (member.getEmail() == null || member.getEmail().trim().isEmpty()) {
            System.out.println("Error: email cannot be empty.");
            return;
        }

        // set join date to today and mark active automatically
        member.setJoinDate(LocalDate.now());
        member.setIsActive(true);

        memberDAO.addMember(member);
    }

    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    public Member getMemberById(int memberId) {
        Member member = memberDAO.getMemberById(memberId);
        if (member == null) {
            System.out.println("Error: member not found.");
        }
        return member;
    }

    public void updateMember(Member member) {
        Member existing = memberDAO.getMemberById(member.getMemberId());
        if (existing == null) {
            System.out.println("Error: member not found.");
            return;
        }
        memberDAO.updateMember(member);
    }

    public void deactivateMember(int memberId) {
        Member existing = memberDAO.getMemberById(memberId);
        if (existing == null) {
            System.out.println("Error: member not found.");
            return;
        }
        // business rule — deactivate instead of delete
        // because borrow history must stay linked to this member
        existing.setIsActive(false);
        memberDAO.updateMember(existing);
        System.out.println("Member deactivated successfully.");
    }
}
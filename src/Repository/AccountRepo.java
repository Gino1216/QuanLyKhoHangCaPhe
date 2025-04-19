package Repository;

import DTO.Account;

import java.util.List;

public interface AccountRepo {

    boolean kiemTraUsernameTonTai(String username);

    Account checkLogin(String username, String password);

    void themAccount(Account account);

    boolean suaAccount(Account account);

    boolean xoaAccount(String username);

    List<Account> timKiemAccount(String keyword);

    List<Account> layDanhSachAccount();


}
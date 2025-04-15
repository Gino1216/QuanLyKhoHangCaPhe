//package Bo;
//
//import DTO.Account;
//import Dao.AccountDao;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class AccountBo {
//
//    private AccountDao Acdao;
//    private ArrayList<Account> AccountList;
//
//    public AccountBo() throws SQLException {
//        super();
//        Acdao = new AccountDao();
//        refresh();
//    }
//
//    public void refresh() {
//        AccountList = Acdao.getListAccount();
//    }
//
//    public boolean checkLogin(String tenDangNhap, String matKhau) {
//        try {
//            boolean result = Acdao.checkAccount(tenDangNhap, matKhau);
//            if (result) {
//                return true;
//            }
//        } catch (Exception e) { // Bắt Exception thay vì SQLException
//            System.err.println("Lỗi khi kiểm tra đăng nhập: " + e.getMessage());
//            return false;
//        }
//        return false;
//    }
//
//    public ArrayList<Account> getAccountList() {
//        return AccountList;
//    }
//
//    public Account getAccount(String maNV) {
//        for (Account ac : AccountList) {
//            if (ac.getMaNV().equals(maNV)) {
//                return ac;
//            }
//        }
//        return null;
//    }
//
//    public ArrayList<Account> getListAccountByRole(String maQuyen) {
//        ArrayList<Account> ds = new ArrayList<>();
//        for (Account ac : AccountList) {
//            if (ac.getMaQuyen().equals(maQuyen)) {
//                ds.add(ac);
//            }
//        }
//        return ds;
//    }
//
//    public ArrayList<Account> getListAccountByName(String name) {
//        ArrayList<Account> ds = new ArrayList<>();
//        for (Account ac : AccountList) {
//            if (ac.getTenDangNhap().toLowerCase().contains(name.toLowerCase())) {
//                ds.add(ac);
//            }
//        }
//        return ds;
//    }
//
//    public boolean addAccount(String maNV, String maQuyen, String tenDangNhap, String matKhau, String tinhTrang) {
//        if (!checkUsername(tenDangNhap, maNV)) {
//            return false;
//        }
//        boolean isInserted = Acdao.insertAccount(maNV, maQuyen, tenDangNhap, matKhau, tinhTrang);
//        if (!isInserted) {
//            return false;
//        }
//        AccountList.add(new Account(maNV, maQuyen, tenDangNhap, matKhau, tinhTrang));
//        return true;
//    }
//
//    public boolean updateAccount(Account ac) {
//        if (!checkUsername(ac.getTenDangNhap(), ac.getMaNV())) {
//            return false;
//        }
//        if (Acdao.updateAccount(ac)) {
//            for (Account a : AccountList) {
//                if (a.getMaNV().equals(ac.getMaNV())) {
//                    a.setMaQuyen(ac.getMaQuyen());
//                    a.setTenDangNhap(ac.getTenDangNhap());
//                    a.setMatKhau(ac.getMatKhau());
//                    a.setTinhTrang(ac.getTinhTrang());
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public boolean deleteAccount(String maNV) {
//        if (Acdao.deleteAccount(maNV)) {
//            AccountList.removeIf(a -> a.getMaNV().equals(maNV));
//            return true;
//        }
//        return false;
//    }
//
//    public boolean checkUsername(String tenDangNhap, String maNV) {
//        for (Account ac : AccountList) {
//            if (ac.getMaNV().equals(maNV)) {
//                continue;
//            }
//            if (ac.getTenDangNhap().equals(tenDangNhap)) {
//                return false;
//            }
//        }
//        return true;
//    }
//}

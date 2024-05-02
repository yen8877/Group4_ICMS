//package com.example.group4_icms.Functions.DAO;
//
//import com.example.group4_icms.Functions.DTO.DependentDTO;
//
//public class DependentService {
//
//    private CustomerDAO customerDAO;
//    private DependentDAO dependentDAO;
//
//    public DependentService(CustomerDAO customerDAO, DependentDAO dependentDAO) {
//        this.customerDAO = customerDAO;
//        this.dependentDAO = dependentDAO;
//    }
//
//    public boolean addDependent(DependentDTO dependent) {
//        // 고객 기본 정보를 customer 테이블에 추가하고, 성공적으로 추가되면 생성된 ID로 dependents 테이블에 정보 추가
//        int customerId = customerDAO.addCustomer(dependent);
//        if (customerId != -1) {
//            return dependentDAO.addDependent(customerId, dependent.getPolicyHolderId());
//        }
//        return false;
//    }
//
//}

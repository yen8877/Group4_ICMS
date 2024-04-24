package com.example.group4_icms.entities.InsuranceEmployee;

import entities.Claim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InsuranceSurveyor extends InsuranceEmployee {
    private Map<Claim,String> RequestClaimsFromManager = new HashMap<>();

    public Map<Claim, String> getRequestClaimsFromManager() {
        return RequestClaimsFromManager;
    }

    public InsuranceSurveyor(String ID, String name, String password) {
        super(ID, name, password);
    }

    @Override
    public ArrayList<Claim> getRequestClaims() {
        return super.getRequestClaims();
    }
}

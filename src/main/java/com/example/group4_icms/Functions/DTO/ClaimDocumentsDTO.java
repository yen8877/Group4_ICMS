package com.example.group4_icms.Functions.DTO;

import java.sql.Timestamp;
/**
 * @author <Group 4>
 */
public class ClaimDocumentsDTO {
    public ClaimDocumentsDTO() {
    }

    private String document_name;
    private String claim_id;
    private Timestamp upload_time;


    public ClaimDocumentsDTO(String document_name, String claim_id) {
        this.document_name = document_name;
        this.claim_id = claim_id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getClaim_id() {
        return claim_id;
    }

    public void setClaim_id(String claim_id) {
        this.claim_id = claim_id;
    }

    public Timestamp getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Timestamp upload_time) {
        this.upload_time = upload_time;
    }
}
package com.netty.serializer.protobuf.transaction;

public class MessageRequest {

    private String xid;
    private long branchId;
    private String resourceId;
    private String applicationData;

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getApplicationData() {
        return applicationData;
    }

    public void setApplicationData(String applicationData) {
        this.applicationData = applicationData;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "xid='" + xid + '\'' +
                ", branchId=" + branchId +
                ", resourceId='" + resourceId + '\'' +
                ", applicationData='" + applicationData + '\'' +
                '}';
    }
}

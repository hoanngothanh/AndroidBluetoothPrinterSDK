package net.fullsnackdev.escpos.models;

import android.os.Parcel;
import android.os.Parcelable;


public class HistoryDetailRes implements Parcelable {

    public static final Parcelable.Creator<HistoryDetailRes> CREATOR = new Parcelable.Creator<HistoryDetailRes>() {
        @Override
        public HistoryDetailRes createFromParcel(Parcel source) {
            return new HistoryDetailRes(source);
        }

        @Override
        public HistoryDetailRes[] newArray(int size) {
            return new HistoryDetailRes[size];
        }
    };

    public Integer rowNumber;

    public Integer payId;

    public String refNo;

    public String paymentDate;

    public String paymentBy;

    public Double amount;

    public String currency;

    public String prodDesc;

    public Integer status;

    public String customerName;

    public String customerEmail;

    public String remark;

    public String description;

    public Integer transactTimeMils;

    public String svrUtcOffset;

    public String paymentType;

    public String orderType;

    public String transId;

    public Integer voidStatus;

    public Integer paymentId;

    public HistoryDetailRes() {
    }

    protected HistoryDetailRes(Parcel in) {
        this.rowNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.payId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.refNo = in.readString();
        this.paymentDate = in.readString();
        this.paymentBy = in.readString();
        this.amount = (Double) in.readValue(Double.class.getClassLoader());
        this.currency = in.readString();
        this.prodDesc = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.customerName = in.readString();
        this.customerEmail = in.readString();
        this.remark = in.readString();
        this.description = in.readString();
        this.transactTimeMils = (Integer) in.readValue(Integer.class.getClassLoader());
        this.svrUtcOffset = in.readString();
        this.paymentType = in.readString();
        this.orderType = in.readString();
        this.transId = in.readString();
        this.voidStatus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.paymentId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.rowNumber);
        dest.writeValue(this.payId);
        dest.writeString(this.refNo);
        dest.writeString(this.paymentDate);
        dest.writeString(this.paymentBy);
        dest.writeValue(this.amount);
        dest.writeString(this.currency);
        dest.writeString(this.prodDesc);
        dest.writeValue(this.status);
        dest.writeString(this.customerName);
        dest.writeString(this.customerEmail);
        dest.writeString(this.remark);
        dest.writeString(this.description);
        dest.writeValue(this.transactTimeMils);
        dest.writeString(this.svrUtcOffset);
        dest.writeString(this.paymentType);
        dest.writeString(this.orderType);
        dest.writeString(this.transId);
        dest.writeValue(this.voidStatus);
        dest.writeValue(this.paymentId);
    }
}

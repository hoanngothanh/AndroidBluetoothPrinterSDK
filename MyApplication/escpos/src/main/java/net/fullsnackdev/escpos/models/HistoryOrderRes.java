package net.fullsnackdev.escpos.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryOrderRes implements Parcelable {


    public String OrderDetailId;

    public String ProductId;

    public String ProductName;

    public String ProductQuatity;

    public String Price;

    public Double Amount;

    public String DiscounAmount;

    public String Currency;

    public String MerchantCode;

    public String MerchantName;

    public String ProductImage;

    public HistoryOrderRes() {
    }

    public HistoryOrderRes(String orderDetailId, String productId, String productName, String productQuatity, String price, Double amount, String discounAmount, String currency, String merchantCode, String merchantName, String productImage) {
        OrderDetailId = orderDetailId;
        ProductId = productId;
        ProductName = productName;
        ProductQuatity = productQuatity;
        Price = price;
        Amount = amount;
        DiscounAmount = discounAmount;
        Currency = currency;
        MerchantCode = merchantCode;
        MerchantName = merchantName;
        ProductImage = productImage;
    }

    public String getOrderDetailId() {
        return OrderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        OrderDetailId = orderDetailId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductQuatity() {
        return ProductQuatity;
    }

    public void setProductQuatity(String productQuatity) {
        ProductQuatity = productQuatity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getDiscounAmount() {
        return DiscounAmount;
    }

    public void setDiscounAmount(String discounAmount) {
        DiscounAmount = discounAmount;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getMerchantCode() {
        return MerchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        MerchantCode = merchantCode;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public void setMerchantName(String merchantName) {
        MerchantName = merchantName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.OrderDetailId);
        dest.writeString(this.ProductId);
        dest.writeString(this.ProductName);
        dest.writeString(this.ProductQuatity);
        dest.writeString(this.Price);
        dest.writeValue(this.Amount);
        dest.writeString(this.DiscounAmount);
        dest.writeString(this.Currency);
        dest.writeString(this.MerchantCode);
        dest.writeString(this.MerchantName);
        dest.writeString(this.ProductImage);
    }

    protected HistoryOrderRes(Parcel in) {
        this.OrderDetailId = in.readString();
        this.ProductId = in.readString();
        this.ProductName = in.readString();
        this.ProductQuatity = in.readString();
        this.Price = in.readString();
        this.Amount = (Double) in.readValue(Double.class.getClassLoader());
        this.DiscounAmount = in.readString();
        this.Currency = in.readString();
        this.MerchantCode = in.readString();
        this.MerchantName = in.readString();
        this.ProductImage = in.readString();
    }

    public static final Creator<HistoryOrderRes> CREATOR = new Creator<HistoryOrderRes>() {
        @Override
        public HistoryOrderRes createFromParcel(Parcel source) {
            return new HistoryOrderRes(source);
        }

        @Override
        public HistoryOrderRes[] newArray(int size) {
            return new HistoryOrderRes[size];
        }
    };
}

package net.fullsnackdev.escpos.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryOrderRes implements Parcelable {


    public static final Creator<HistoryOrderRes> CREATOR = new Creator<HistoryOrderRes>() {
        @Override
        public HistoryOrderRes createFromParcel(Parcel in) {
            return new HistoryOrderRes(in);
        }

        @Override
        public HistoryOrderRes[] newArray(int size) {
            return new HistoryOrderRes[size];
        }
    };

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

    protected HistoryOrderRes(Parcel in) {
        OrderDetailId = in.readString();
        ProductId = in.readString();
        ProductName = in.readString();
        ProductQuatity = in.readString();
        Price = in.readString();
        Amount = Double.valueOf(in.readString());
        DiscounAmount = in.readString();
        Currency = in.readString();
        MerchantCode = in.readString();
        MerchantName = in.readString();
        ProductImage = in.readString();
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
        dest.writeString(this.ProductId);
        dest.writeDouble(this.Amount);
        dest.writeString(this.DiscounAmount);
        dest.writeString(this.Price);
        dest.writeString(this.MerchantCode);
        dest.writeString(this.MerchantName);
        dest.writeString(this.ProductImage);
    }
}

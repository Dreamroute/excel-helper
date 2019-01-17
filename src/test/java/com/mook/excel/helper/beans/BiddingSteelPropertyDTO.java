/**
 * 
 */
package com.mook.excel.helper.beans;

import java.io.Serializable;

import com.github.dreamroute.excel.helper.annotation.Column;
import com.github.dreamroute.excel.helper.annotation.Sheet;

/**
 * 导入模板
 *
 * @author: niechen
 * @date: 2018/12/23
 **/
@Sheet(name = "招标内容")
public class BiddingSteelPropertyDTO implements Serializable {
    private static final long serialVersionUID = -5225613131903515283L;

    /**
     * 存货编码
     */
    @Column(name = "存货编码")
    private String cinvcode;
    /**
     * 物料名称
     */
    @Column(name = "物料名称")
    private String goodsName;
    /**
     * 型号规格
     */
    @Column(name = "型号规格")
    private String modelNumber;
    /**
     * 计量单位(不和数据字典关联)
     */
    @Column(name = "计量单位")
    private String unit;
    /**
     * 采购数量
     */
    @Column(name = "采购数量")
    private Double purchaseQuantity;
    /**
     * 交货期
     */
    @Column(name = "交货期")
    private Integer dateOfDelivery;
    /**
     * 参考价格
     */
    @Column(name = "参考单价(元)")
    private Double referencePrice;
    /**
     * 所属类目
     */
    @Column(name = "所属类目")
    private String category;
    /**
     * 生产厂家（1.6.2改为备注）
     */
    @Column(name = "备注")
    private String manufacturer;

    /**
     * @return the cinvcode
     */
    public String getCinvcode() {
        return cinvcode;
    }

    /**
     * @param cinvcode the cinvcode to set
     */
    public void setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
    }

    /**
     * @return the goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * @param goodsName the goodsName to set
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * @return the modelNumber
     */
    public String getModelNumber() {
        return modelNumber;
    }

    /**
     * @param modelNumber the modelNumber to set
     */
    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the purchaseQuantity
     */
    public Double getPurchaseQuantity() {
        return purchaseQuantity;
    }

    /**
     * @param purchaseQuantity the purchaseQuantity to set
     */
    public void setPurchaseQuantity(Double purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    /**
     * @return the dateOfDelivery
     */
    public Integer getDateOfDelivery() {
        return dateOfDelivery;
    }

    /**
     * @param dateOfDelivery the dateOfDelivery to set
     */
    public void setDateOfDelivery(Integer dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    /**
     * @return the referencePrice
     */
    public Double getReferencePrice() {
        return referencePrice;
    }

    /**
     * @param referencePrice the referencePrice to set
     */
    public void setReferencePrice(Double referencePrice) {
        this.referencePrice = referencePrice;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}

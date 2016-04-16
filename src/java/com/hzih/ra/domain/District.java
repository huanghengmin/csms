package com.hzih.ra.domain;

/**
 * 帐号，用户
 *
 * @author www
 * @hibernate.class table="district"
 */
public class District {

    /**
     * @hibernate.id column="district_id" generator-class="increment" type="long"
     *               length="11"
     */
    String id;

    /**
     * 地区名
     *
     * @hibernate.property column="district_name" type="string" length="20"
     */
    String districtName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

}

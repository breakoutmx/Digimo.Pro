/*Copyright (c) 2017-2018 Breakout.MX All Rights Reserved.
 This software is the confidential and proprietary information of breakout.mx You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with Breakout.MX*/
package com.digimo_pro.herokumysqldmp1.dao;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wavemaker.runtime.data.dao.WMGenericDaoImpl;

import com.digimo_pro.herokumysqldmp1.Charge;

/**
 * Specifies methods used to obtain and modify Charge related information
 * which is stored in the database.
 */
@Repository("HerokuMysqlDMP1.ChargeDao")
public class ChargeDao extends WMGenericDaoImpl<Charge, Integer> {

    @Autowired
    @Qualifier("HerokuMysqlDMP1Template")
    private HibernateTemplate template;

    public HibernateTemplate getTemplate() {
        return this.template;
    }
}


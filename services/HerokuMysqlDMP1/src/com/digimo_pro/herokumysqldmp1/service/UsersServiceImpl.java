/*Copyright (c) 2017-2018 Breakout.MX All Rights Reserved.
 This software is the confidential and proprietary information of breakout.mx You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with Breakout.MX*/
package com.digimo_pro.herokumysqldmp1.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.digimo_pro.herokumysqldmp1.Card;
import com.digimo_pro.herokumysqldmp1.Customer;
import com.digimo_pro.herokumysqldmp1.Profile;
import com.digimo_pro.herokumysqldmp1.Users;


/**
 * ServiceImpl object for domain model class Users.
 *
 * @see Users
 */
@Service("HerokuMysqlDMP1.UsersService")
public class UsersServiceImpl implements UsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
	@Qualifier("HerokuMysqlDMP1.CardService")
	private CardService cardService;

    @Autowired
	@Qualifier("HerokuMysqlDMP1.ProfileService")
	private ProfileService profileService;

    @Autowired
	@Qualifier("HerokuMysqlDMP1.CustomerService")
	private CustomerService customerService;

    @Autowired
    @Qualifier("HerokuMysqlDMP1.UsersDao")
    private WMGenericDao<Users, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Users, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "HerokuMysqlDMP1TransactionManager")
    @Override
	public Users create(Users users) {
        LOGGER.debug("Creating a new Users with information: {}", users);
        Users usersCreated = this.wmGenericDao.create(users);
        if(usersCreated.getCards() != null) {
            for(Card card : usersCreated.getCards()) {
                card.setUsers(usersCreated);
                LOGGER.debug("Creating a new child Card with information: {}", card);
                cardService.create(card);
            }
        }

        if(usersCreated.getCustomers() != null) {
            for(Customer customer : usersCreated.getCustomers()) {
                customer.setUsers(usersCreated);
                LOGGER.debug("Creating a new child Customer with information: {}", customer);
                customerService.create(customer);
            }
        }

        if(usersCreated.getProfiles() != null) {
            for(Profile profile : usersCreated.getProfiles()) {
                profile.setUsers(usersCreated);
                LOGGER.debug("Creating a new child Profile with information: {}", profile);
                profileService.create(profile);
            }
        }
        return usersCreated;
    }

	@Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public Users getById(Integer usersId) throws EntityNotFoundException {
        LOGGER.debug("Finding Users by id: {}", usersId);
        Users users = this.wmGenericDao.findById(usersId);
        if (users == null){
            LOGGER.debug("No Users found with id: {}", usersId);
            throw new EntityNotFoundException(String.valueOf(usersId));
        }
        return users;
    }

    @Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public Users findById(Integer usersId) {
        LOGGER.debug("Finding Users by id: {}", usersId);
        return this.wmGenericDao.findById(usersId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public Users update(Users users) throws EntityNotFoundException {
        LOGGER.debug("Updating Users with information: {}", users);
        this.wmGenericDao.update(users);

        Integer usersId = users.getId();

        return this.wmGenericDao.findById(usersId);
    }

    @Transactional(value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public Users delete(Integer usersId) throws EntityNotFoundException {
        LOGGER.debug("Deleting Users with id: {}", usersId);
        Users deleted = this.wmGenericDao.findById(usersId);
        if (deleted == null) {
            LOGGER.debug("No Users found with id: {}", usersId);
            throw new EntityNotFoundException(String.valueOf(usersId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public Page<Users> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Users");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
    @Override
    public Page<Users> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Users");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service HerokuMysqlDMP1 for table Users to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

	@Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
	@Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }

    @Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
    @Override
    public Page<Card> findAssociatedCards(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated cards");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("users.id = '" + id + "'");

        return cardService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
    @Override
    public Page<Customer> findAssociatedCustomers(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated customers");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("users.id = '" + id + "'");

        return customerService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
    @Override
    public Page<Profile> findAssociatedProfiles(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated profiles");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("users.id = '" + id + "'");

        return profileService.findAll(queryBuilder.toString(), pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service CardService instance
	 */
	protected void setCardService(CardService service) {
        this.cardService = service;
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service ProfileService instance
	 */
	protected void setProfileService(ProfileService service) {
        this.profileService = service;
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service CustomerService instance
	 */
	protected void setCustomerService(CustomerService service) {
        this.customerService = service;
    }

}


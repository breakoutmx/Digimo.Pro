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
import com.digimo_pro.herokumysqldmp1.Profile;


/**
 * ServiceImpl object for domain model class Profile.
 *
 * @see Profile
 */
@Service("HerokuMysqlDMP1.ProfileService")
public class ProfileServiceImpl implements ProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Autowired
	@Qualifier("HerokuMysqlDMP1.CardService")
	private CardService cardService;

    @Autowired
    @Qualifier("HerokuMysqlDMP1.ProfileDao")
    private WMGenericDao<Profile, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Profile, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "HerokuMysqlDMP1TransactionManager")
    @Override
	public Profile create(Profile profile) {
        LOGGER.debug("Creating a new Profile with information: {}", profile);
        Profile profileCreated = this.wmGenericDao.create(profile);
        if(profileCreated.getCards() != null) {
            for(Card card : profileCreated.getCards()) {
                card.setProfileByProfile(profileCreated);
                LOGGER.debug("Creating a new child Card with information: {}", card);
                cardService.create(card);
            }
        }
        return profileCreated;
    }

	@Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public Profile getById(Integer profileId) throws EntityNotFoundException {
        LOGGER.debug("Finding Profile by id: {}", profileId);
        Profile profile = this.wmGenericDao.findById(profileId);
        if (profile == null){
            LOGGER.debug("No Profile found with id: {}", profileId);
            throw new EntityNotFoundException(String.valueOf(profileId));
        }
        return profile;
    }

    @Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public Profile findById(Integer profileId) {
        LOGGER.debug("Finding Profile by id: {}", profileId);
        return this.wmGenericDao.findById(profileId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public Profile update(Profile profile) throws EntityNotFoundException {
        LOGGER.debug("Updating Profile with information: {}", profile);
        this.wmGenericDao.update(profile);

        Integer profileId = profile.getId();

        return this.wmGenericDao.findById(profileId);
    }

    @Transactional(value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public Profile delete(Integer profileId) throws EntityNotFoundException {
        LOGGER.debug("Deleting Profile with id: {}", profileId);
        Profile deleted = this.wmGenericDao.findById(profileId);
        if (deleted == null) {
            LOGGER.debug("No Profile found with id: {}", profileId);
            throw new EntityNotFoundException(String.valueOf(profileId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
	@Override
	public Page<Profile> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Profiles");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
    @Override
    public Page<Profile> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Profiles");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "HerokuMysqlDMP1TransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service HerokuMysqlDMP1 for table Profile to {} format", exportType);
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
        queryBuilder.append("profileByProfile.id = '" + id + "'");

        return cardService.findAll(queryBuilder.toString(), pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service CardService instance
	 */
	protected void setCardService(CardService service) {
        this.cardService = service;
    }

}


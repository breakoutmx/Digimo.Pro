/*Copyright (c) 2017-2018 Breakout.MX All Rights Reserved.
 This software is the confidential and proprietary information of breakout.mx You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with Breakout.MX*/
package com.digimo_pro.herokumysqldmp1.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.digimo_pro.herokumysqldmp1.Card;

/**
 * Service object for domain model class {@link Card}.
 */
public interface CardService {

    /**
     * Creates a new Card. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Card if any.
     *
     * @param card Details of the Card to be created; value cannot be null.
     * @return The newly created Card.
     */
	Card create(Card card);


	/**
	 * Returns Card by given id if exists.
	 *
	 * @param cardId The id of the Card to get; value cannot be null.
	 * @return Card associated with the given cardId.
     * @throws EntityNotFoundException If no Card is found.
	 */
	Card getById(Integer cardId) throws EntityNotFoundException;

    /**
	 * Find and return the Card by given id if exists, returns null otherwise.
	 *
	 * @param cardId The id of the Card to get; value cannot be null.
	 * @return Card associated with the given cardId.
	 */
	Card findById(Integer cardId);


	/**
	 * Updates the details of an existing Card. It replaces all fields of the existing Card with the given card.
	 *
     * This method overrides the input field values using Server side or database managed properties defined on Card if any.
     *
	 * @param card The details of the Card to be updated; value cannot be null.
	 * @return The updated Card.
	 * @throws EntityNotFoundException if no Card is found with given input.
	 */
	Card update(Card card) throws EntityNotFoundException;

    /**
	 * Deletes an existing Card with the given id.
	 *
	 * @param cardId The id of the Card to be deleted; value cannot be null.
	 * @return The deleted Card.
	 * @throws EntityNotFoundException if no Card found with the given id.
	 */
	Card delete(Integer cardId) throws EntityNotFoundException;

	/**
	 * Find all Cards matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
	 *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
	 *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Cards.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
	 */
    @Deprecated
	Page<Card> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
	 * Find all Cards matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
	 *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Cards.
     *
     * @see Pageable
     * @see Page
	 */
    Page<Card> findAll(String query, Pageable pageable);

    /**
	 * Exports all Cards matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
	 *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
	 */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

	/**
	 * Retrieve the count of the Cards in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
	 * @return The count of the Card.
	 */
	long count(String query);

	/**
	 * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
	 * @return Paginated data with included fields.

     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
	Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}


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
import com.digimo_pro.herokumysqldmp1.Customer;

/**
 * Service object for domain model class {@link Customer}.
 */
public interface CustomerService {

    /**
     * Creates a new Customer. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Customer if any.
     *
     * @param customer Details of the Customer to be created; value cannot be null.
     * @return The newly created Customer.
     */
	Customer create(Customer customer);


	/**
	 * Returns Customer by given id if exists.
	 *
	 * @param customerId The id of the Customer to get; value cannot be null.
	 * @return Customer associated with the given customerId.
     * @throws EntityNotFoundException If no Customer is found.
	 */
	Customer getById(Integer customerId) throws EntityNotFoundException;

    /**
	 * Find and return the Customer by given id if exists, returns null otherwise.
	 *
	 * @param customerId The id of the Customer to get; value cannot be null.
	 * @return Customer associated with the given customerId.
	 */
	Customer findById(Integer customerId);


	/**
	 * Updates the details of an existing Customer. It replaces all fields of the existing Customer with the given customer.
	 *
     * This method overrides the input field values using Server side or database managed properties defined on Customer if any.
     *
	 * @param customer The details of the Customer to be updated; value cannot be null.
	 * @return The updated Customer.
	 * @throws EntityNotFoundException if no Customer is found with given input.
	 */
	Customer update(Customer customer) throws EntityNotFoundException;

    /**
	 * Deletes an existing Customer with the given id.
	 *
	 * @param customerId The id of the Customer to be deleted; value cannot be null.
	 * @return The deleted Customer.
	 * @throws EntityNotFoundException if no Customer found with the given id.
	 */
	Customer delete(Integer customerId) throws EntityNotFoundException;

	/**
	 * Find all Customers matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
	 *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
	 *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Customers.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
	 */
    @Deprecated
	Page<Customer> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
	 * Find all Customers matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
	 *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Customers.
     *
     * @see Pageable
     * @see Page
	 */
    Page<Customer> findAll(String query, Pageable pageable);

    /**
	 * Exports all Customers matching the given input query to the given exportType format.
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
	 * Retrieve the count of the Customers in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
	 * @return The count of the Customer.
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

    /*
     * Returns the associated cards for given Customer id.
     *
     * @param id value of id; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated Card instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<Card> findAssociatedCards(Integer id, Pageable pageable);

}


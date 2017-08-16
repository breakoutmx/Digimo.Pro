/*Copyright (c) 2017-2018 Breakout.MX All Rights Reserved.
 This software is the confidential and proprietary information of breakout.mx You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with Breakout.MX*/
package com.digimo_pro.herokumysqldmp1.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.digimo_pro.herokumysqldmp1.Card;
import com.digimo_pro.herokumysqldmp1.service.CardService;


/**
 * Controller object for domain model class Card.
 * @see Card
 */
@RestController("HerokuMysqlDMP1.CardController")
@Api(value = "CardController", description = "Exposes APIs to work with Card resource.")
@RequestMapping("/HerokuMysqlDMP1/Card")
public class CardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    @Autowired
	@Qualifier("HerokuMysqlDMP1.CardService")
	private CardService cardService;

	@ApiOperation(value = "Creates a new Card instance.")
	@RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Card createCard(@RequestBody Card card) {
		LOGGER.debug("Create Card with information: {}" , card);

		card = cardService.create(card);
		LOGGER.debug("Created Card with information: {}" , card);

	    return card;
	}


    @ApiOperation(value = "Returns the Card instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Card getCard(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Card with id: {}" , id);

        Card foundCard = cardService.getById(id);
        LOGGER.debug("Card details with id: {}" , foundCard);

        return foundCard;
    }

    @ApiOperation(value = "Updates the Card instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Card editCard(@PathVariable("id") Integer id, @RequestBody Card card) throws EntityNotFoundException {
        LOGGER.debug("Editing Card with id: {}" , card.getId());

        card.setId(id);
        card = cardService.update(card);
        LOGGER.debug("Card details with id: {}" , card);

        return card;
    }

    @ApiOperation(value = "Deletes the Card instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteCard(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Card with id: {}" , id);

        Card deletedCard = cardService.delete(id);

        return deletedCard != null;
    }

    /**
     * @deprecated Use {@link #findCards(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Card instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Card> searchCardsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Cards list");
        return cardService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Card instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Card> findCards(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Cards list");
        return cardService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Card instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Card> filterCards(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Cards list");
        return cardService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportCards(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return cardService.export(exportType, query, pageable);
    }

	@ApiOperation(value = "Returns the total count of Card instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countCards( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Cards");
		return cardService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getCardAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return cardService.getAggregatedValues(aggregationInfo, pageable);
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


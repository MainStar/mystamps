/*
 * Copyright (C) 2012 Slava Semushin <slava.semushin@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package ru.mystamps.web.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkState;

import ru.mystamps.web.dao.UsersActivationDao;
import ru.mystamps.web.entity.UsersActivation;

@Service
public class CronService {
	
	public static final int PURGE_UNACTIVATED_REQUEST_AFTER_DAYS = 3;
	
	private static final long CHECK_UNACTIVATED_REQUESTS_PERIOD = 12 * DateUtils.MILLIS_PER_HOUR;
	
	private static final Logger LOG = LoggerFactory.getLogger(CronService.class);
	
	@Inject
	private UsersActivationDao usersActivationDao;
	
	@Scheduled(fixedDelay = CHECK_UNACTIVATED_REQUESTS_PERIOD)
	@Transactional
	public void purgeUsersActivations() {
		final Date expiredSince = DateUtils.addDays(
			new Date(),
			-PURGE_UNACTIVATED_REQUEST_AFTER_DAYS
		);
		
		final List<UsersActivation> expiredActivations =
			usersActivationDao.findByCreatedAtLessThan(expiredSince);
		
		checkState(expiredActivations != null, "Expired activations should be non null");
		
		if (expiredActivations.isEmpty()) {
			LOG.info("Expired activations was not found.");
			return;
		}
		
		for (final UsersActivation activation : expiredActivations) {
			LOG.info(
				"Delete expired activation (key: {}, email: {}, created: {})",
				new Object[] {
					activation.getActivationKey(),
					activation.getEmail(),
					activation.getCreatedAt()
				}
			);
		}
		
		usersActivationDao.delete(expiredActivations);
	}
	
}

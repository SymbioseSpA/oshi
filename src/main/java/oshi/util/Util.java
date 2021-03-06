/**
 * Oshi (https://github.com/dblock/oshi)
 * 
 * Copyright (c) 2010 - 2016 The Oshi Project Team 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * dblock[at]dblock[dot]org 
 * alessandro[at]perucchi[dot]org
 * widdis[at]gmail[dot]com 
 * https://github.com/dblock/oshi/graphs/contributors
 */
package oshi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General utility methods
 * 
 * @author widdis[at]gmail[dot]com
 */
public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    /**
     * Sleeps for the specified number of milliseconds.
     * 
     * @param ms
     *            How long to sleep
     */
    public static void sleep(long ms) {
        try {
            LOG.trace("Sleeping for {} ms", ms);
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            LOG.trace("", e);
            LOG.warn("Interrupted while sleeping for {} ms", ms);
        }
    }

    /**
     * Sleeps for the specified number of milliseconds after the given system
     * time in milliseconds. If that number of milliseconds has already elapsed,
     * does nothing.
     * 
     * @param startTime
     *            System time in milliseconds to sleep after
     * @param ms
     *            How long after startTime to sleep
     */
    public static void sleepAfter(long startTime, long ms) {
        long now = System.currentTimeMillis();
        long until = startTime + ms;
        LOG.trace("Sleeping until {}", until);
        if (now < until) {
            sleep(until - now);
        }
    }
}
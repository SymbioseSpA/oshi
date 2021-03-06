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
package oshi.hardware;

import oshi.json.OshiJsonObject;

/**
 * The Power Source is one or more batteries with some capacity, and some state
 * of charge/discharge
 * 
 * @author widdis[at]gmail[dot]com
 */
public interface PowerSource extends OshiJsonObject {
    /**
     * Name of the power source (e.g., InternalBattery-0)
     * 
     * @return The power source name
     */
    String getName();

    /**
     * Remaining capacity as a fraction of max capacity.
     * 
     * @return A value between 0.0 (fully drained) and 1.0 (fully charged)
     */
    double getRemainingCapacity();

    /**
     * Estimated time remaining on the power source, in seconds.
     * 
     * @return If positive, seconds remaining. If negative, -1.0 (calculating)
     *         or -2.0 (unlimited)
     */
    double getTimeRemaining();
}

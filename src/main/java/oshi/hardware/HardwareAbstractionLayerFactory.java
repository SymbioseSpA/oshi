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

import com.sun.jna.Platform;

import oshi.PlatformEnum;
import oshi.hardware.platform.linux.LinuxHardwareAbstractionLayer;
import oshi.hardware.platform.mac.MacHardwareAbstractionLayer;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;

/**
 * Factory to create instances of {@link HardwareAbstractionLayer}
 * 
 * @author pcollaog
 * @since 2.3
 */
public class HardwareAbstractionLayerFactory {

	/**
	 * @param platform
	 * @return HAL instance
	 */
	public static final HardwareAbstractionLayer createInstance(
			PlatformEnum platform) {

		switch (platform) {
		case WINDOWS:
			return new WindowsHardwareAbstractionLayer();
		case LINUX:
			return new LinuxHardwareAbstractionLayer();
		case MACOSX:
			return new MacHardwareAbstractionLayer();
		default:
			throw new RuntimeException(
					"Operating system not supported: " + Platform.getOSType());
		}

	}

}

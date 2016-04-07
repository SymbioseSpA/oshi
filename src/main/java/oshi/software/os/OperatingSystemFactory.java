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
package oshi.software.os;

import com.sun.jna.Platform;

import oshi.PlatformEnum;
import oshi.software.os.linux.LinuxOperatingSystem;
import oshi.software.os.mac.MacOperatingSystem;
import oshi.software.os.windows.WindowsOperatingSystem;

/**
 * @author pcollaog
 * @since 2.3
 */
public class OperatingSystemFactory {

	/**
	 * Create instance of {@link OperatingSystem} with {@link PlatformEnum}
	 * 
	 * @param platform
	 * @return instance of {@link OperatingSystem}
	 */
	public static final OperatingSystem createInstance(PlatformEnum platform) {
		switch (platform) {
		case WINDOWS:
			return new WindowsOperatingSystem();
		case LINUX:
			return new LinuxOperatingSystem();
		case MACOSX:
			return new MacOperatingSystem();
		default:
			throw new RuntimeException(
					"Operating system not supported: " + Platform.getOSType());
		}
	}

}

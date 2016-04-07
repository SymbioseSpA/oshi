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
package oshi;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import com.sun.jna.Platform;

import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.HardwareAbstractionLayerFactory;
import oshi.json.NullAwareJsonObjectBuilder;
import oshi.json.OshiJsonObject;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystemFactory;

/**
 * System information. This is the main entry point to Oshi. This object
 * provides getters which instantiate the appropriate platform-specific
 * implementations of {@link OperatingSystem} (software) and
 * {@link HardwareAbstractionLayer} (hardware).
 * 
 * @author dblock[at]dblock[dot]org
 */
public class SystemInfo implements OshiJsonObject {

	private OperatingSystem _os = null;

	private HardwareAbstractionLayer _hardware = null;

	/**
	 * Default constructor
	 */
	public SystemInfo() {
		PlatformEnum currentPlatformEnum = extractPlatform();
		_os = OperatingSystemFactory.createInstance(currentPlatformEnum);
		_hardware = HardwareAbstractionLayerFactory
				.createInstance(currentPlatformEnum);
	}

	/**
	 * 
	 * @return value with platform type
	 * @since 2.3
	 */
	private PlatformEnum extractPlatform() {
		if (Platform.isWindows()) {
			return PlatformEnum.WINDOWS;
		} else if (Platform.isLinux()) {
			return PlatformEnum.LINUX;
		} else if (Platform.isMac()) {
			return PlatformEnum.MACOSX;
		}
		return PlatformEnum.UNKNOWN;
	}

	/**
	 * Creates a new instance of the appropriate platform-specific
	 * {@link OperatingSystem}.
	 * 
	 * @return A new instance of {@link OperatingSystem}.
	 */
	public OperatingSystem getOperatingSystem() {
		return _os;
	}

	/**
	 * Creates a new instance of the appropriate platform-specific
	 * {@link HardwareAbstractionLayer}.
	 * 
	 * @return A new instance of {@link HardwareAbstractionLayer}.
	 */
	public HardwareAbstractionLayer getHardware() {
		return _hardware;
	}

	@Override
	public JsonObject toJSON() {
		JsonBuilderFactory jsonFactory = Json.createBuilderFactory(null);

		return NullAwareJsonObjectBuilder
				.wrap(jsonFactory.createObjectBuilder())
				.add("operatingSystem", _os.toJSON())
				.add("hardware", _hardware.toJSON()).build();
	}
}

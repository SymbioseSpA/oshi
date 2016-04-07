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
import oshi.software.os.linux.LinuxOperatingSystem;
import oshi.software.os.mac.MacOperatingSystem;
import oshi.software.os.windows.WindowsOperatingSystem;

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

	private final PlatformEnum _currentPlatformEnum;

	/**
	 * Default constructor
	 */
	public SystemInfo() {
		if (Platform.isWindows()) {
			_currentPlatformEnum = PlatformEnum.WINDOWS;
		} else if (Platform.isLinux()) {
			_currentPlatformEnum = PlatformEnum.LINUX;
		} else if (Platform.isMac()) {
			_currentPlatformEnum = PlatformEnum.MACOSX;
		} else {
			_currentPlatformEnum = PlatformEnum.UNKNOWN;
		}
	}

	private JsonBuilderFactory jsonFactory = Json.createBuilderFactory(null);

	/**
	 * Creates a new instance of the appropriate platform-specific
	 * {@link OperatingSystem}.
	 * 
	 * @return A new instance of {@link OperatingSystem}.
	 */
	public OperatingSystem getOperatingSystem() {
		if (_os == null) {
			switch (_currentPlatformEnum) {

			case WINDOWS:
				_os = new WindowsOperatingSystem();
				break;
			case LINUX:
				_os = new LinuxOperatingSystem();
				break;
			case MACOSX:
				_os = new MacOperatingSystem();
				break;
			default:
				throw new RuntimeException("Operating system not supported: "
						+ Platform.getOSType());
			}
		}
		return _os;
	}

	/**
	 * Creates a new instance of the appropriate platform-specific
	 * {@link HardwareAbstractionLayer}.
	 * 
	 * @return A new instance of {@link HardwareAbstractionLayer}.
	 */
	public HardwareAbstractionLayer getHardware() {
		return HardwareAbstractionLayerFactory
				.createInstance(_currentPlatformEnum);
	}

	@Override
	public JsonObject toJSON() {
		return NullAwareJsonObjectBuilder
				.wrap(jsonFactory.createObjectBuilder())
				.add("operatingSystem", getOperatingSystem().toJSON())
				.add("hardware", getHardware().toJSON()).build();
	}
}

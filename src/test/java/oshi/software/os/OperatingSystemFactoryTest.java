/**
 * Oshi (https://github.com/dblock/oshi)
 *
 * Copyright (c) 2010 - 2016 The Oshi Project Team
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: dblock[at]dblock[dot]org alessandro[at]perucchi[dot]org
 * widdis[at]gmail[dot]com https://github.com/dblock/oshi/graphs/contributors
 */
package oshi.software.os;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import oshi.PlatformEnum;
import oshi.software.os.linux.LinuxOperatingSystem;
import oshi.software.os.mac.MacOperatingSystem;
import oshi.software.os.windows.WindowsOperatingSystem;

/**
 * @author pcollaog
 * @since 2.3
 */
@RunWith(Parameterized.class)
public class OperatingSystemFactoryTest {

	@Rule
	public ExpectedException _ee = ExpectedException.none();

	private PlatformEnum _platform;

	private Class<? extends OperatingSystem> _platformExpected;

	/**
	 * @param platform
	 * @param platformExpected
	 */
	public OperatingSystemFactoryTest(PlatformEnum platform,
			Class<? extends OperatingSystem> platformExpected) {
		_platform = platform;
		_platformExpected = platformExpected;
	}

	@Test
	public void shouldInstanceOperatingSystem() throws Exception {
		if (null == _platformExpected) {
			_ee.expect(RuntimeException.class);
		}
		OperatingSystem instance = OperatingSystemFactory
				.createInstance(_platform);
		assertTrue(_platformExpected.isInstance(instance));
	}

	@Parameters
	public static Collection<Object[]> osTestData() {
		return Arrays.asList(new Object[][] {
				{ PlatformEnum.LINUX, LinuxOperatingSystem.class },
				{ PlatformEnum.WINDOWS, WindowsOperatingSystem.class },
				{ PlatformEnum.MACOSX, MacOperatingSystem.class },
				{ PlatformEnum.UNKNOWN, null }

		});
	}

}

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
import oshi.hardware.platform.linux.LinuxHardwareAbstractionLayer;
import oshi.hardware.platform.mac.MacHardwareAbstractionLayer;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;

/**
 * @author pcollaog
 * @since 2.3
 */
@RunWith(Parameterized.class)
public class HardwareAbstractionLayerFactoryTest {

	@Rule
	public ExpectedException _ee = ExpectedException.none();

	private PlatformEnum _plarform;

	private Class<? extends HardwareAbstractionLayer> _expectedInstance;

	/**
	 * @param plarform
	 * @param expectedInstance
	 */
	public HardwareAbstractionLayerFactoryTest(PlatformEnum plarform,
			Class<? extends HardwareAbstractionLayer> expectedInstance) {
		_plarform = plarform;
		_expectedInstance = expectedInstance;
	}

	@Test
	public void shouldInstanceHardwareAbstractionLayer() throws Exception {
		if (null == _expectedInstance) {
			_ee.expect(RuntimeException.class);
		}
		HardwareAbstractionLayer halInstance = HardwareAbstractionLayerFactory
				.createInstance(_plarform);

		assertTrue(_expectedInstance.isInstance(halInstance));
	}

	@Parameters
	public static Collection<Object[]> testData() {
		return Arrays.asList(new Object[][] {
				{ PlatformEnum.WINDOWS, WindowsHardwareAbstractionLayer.class },
				{ PlatformEnum.LINUX, LinuxHardwareAbstractionLayer.class },
				{ PlatformEnum.MACOSX, MacHardwareAbstractionLayer.class },
				{ PlatformEnum.UNKNOWN, null } });
	}

}

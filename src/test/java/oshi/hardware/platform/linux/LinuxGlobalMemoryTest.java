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
package oshi.hardware.platform.linux;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import oshi.util.FileUtil;

/**
 * @author pcollaog
 * @since 2.3
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FileUtil.class })
public class LinuxGlobalMemoryTest {

	@Test
	public void testName() throws Exception {
		List<String> values = readFile();
		mockStatic(FileUtil.class);
		when(FileUtil.readFile(anyString())).thenReturn(values);

		LinuxGlobalMemory mem = new LinuxGlobalMemory();

		assertEquals(2008449024, mem.getAvailable());
		assertEquals(2099650560, mem.getTotal());
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private List<String> readFile() throws IOException, URISyntaxException {
		URL resource = getClass().getResource("meminfo_case1.txt");
		List<String> values = Files.readAllLines(Paths.get(resource.toURI()),
				UTF_8);
		return values;
	}

}

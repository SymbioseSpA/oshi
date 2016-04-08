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
package oshi.software.os.mac;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileSystemView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oshi.software.os.OSFileStore;

/**
 * The Mac File System contains {@link OSFileStore}s which are a storage pool,
 * device, partition, volume, concrete file system or other implementation
 * specific means of file storage. In Mac OS X, these are found in the /Volumes
 * directory.
 * 
 * @author widdis[at]gmail[dot]com
 */
public class MacFileSystem {

	private static final String LOCAL_DISK = "Local Disk";

	private static final String NETWORK_DRIVE = "Network Drive";

	private static final String OSX_INDEX_STORE = ".DS_Store";

	private static final String VOLUMES_PATH = "/Volumes";

	private static final Logger LOG = LoggerFactory
			.getLogger(MacFileSystem.class);

	private static final Pattern NETWORK_PATTERN = Pattern
			.compile("^(?:localhost:|//).*");

	/**
	 * Regexp matcher for /dev/disk1 etc
	 */
	private static final Pattern LOCAL_DISK_PATTERN = Pattern
			.compile("/dev/disk\\d");

	/**
	 * Gets File System Information.
	 * 
	 * @return An array of {@link OSFileStore} objects representing mounted
	 *         volumes. May return disconnected volumes with
	 *         {@link OSFileStore#getTotalSpace()} = 0.
	 */
	public static OSFileStore[] getFileStores() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		// Mac file systems are mounted in /Volumes
		File volumes = new File(VOLUMES_PATH);

		List<OSFileStore> fsList = new ArrayList<>();
		if (null != volumes.listFiles()) {
			for (File file : volumes.listFiles()) {
				// Everyone hates DS Store
				if (file.getName().endsWith(OSX_INDEX_STORE)) {
					continue;
				}

				String name = fsv.getSystemDisplayName(file);
				String description = "Volume";
				try {
					if (File.separator.equals(file.getCanonicalPath())) {
						name = name + " (/)";
					}
					FileStore fs = Files.getFileStore(file.toPath());

					String filename = fs.name();
					if (LOCAL_DISK_PATTERN.matcher(filename).matches()) {
						description = LOCAL_DISK;
					} else if (NETWORK_PATTERN.matcher(filename).matches()) {
						description = NETWORK_DRIVE;
					}
				} catch (IOException e) {
					LOG.trace("", e);
					continue;
				}
				fsList.add(new OSFileStore(name, description,
						file.getUsableSpace(), file.getTotalSpace()));
			}
		}
		return fsList.toArray(new OSFileStore[fsList.size()]);
	}
}

/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ratpackframework.file.internal;

import org.ratpackframework.file.FileSystemContext;

import java.io.File;

import static org.ratpackframework.util.internal.CollectionUtils.join;

public class DefaultFileSystemContext implements FileSystemContext {

  private final File file;

  public DefaultFileSystemContext(File file) {
    this.file = file;
  }

  public File getFile() {
    return file;
  }

  public File file(String... path) {
    return new File(file, join(File.separator, (Object[]) path));
  }

  public FileSystemContext context(String... path) {
    return new DefaultFileSystemContext(file(path));
  }

  public FileSystemContext context(File file) {
    return new DefaultFileSystemContext(file);
  }
}

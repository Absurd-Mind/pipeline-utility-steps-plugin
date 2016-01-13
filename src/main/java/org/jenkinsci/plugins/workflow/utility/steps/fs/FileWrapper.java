/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 CloudBees Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.jenkinsci.plugins.workflow.utility.steps.fs;

import hudson.FilePath;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Serializable;

/**
 * Contains serializable information about a file name.
 *
 * @author Robert Sandell &lt;rsandell@cloudbees.com&gt;.
 */
public class FileWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    @Nonnull
    private final String name;
    @Nonnull
    private final String path;
    private final boolean directory;
    private final long length;
    private final long lastModified;

    public FileWrapper(@Nonnull String name, @Nonnull String path, boolean directory, long length, long lastModified) {
        this.name = name;
        this.directory = directory;
        this.length = length;
        this.lastModified = lastModified;
        if (directory && !path.endsWith("/")) {
            this.path = path + "/";
        } else {
            this.path = path;
        }
    }

    protected FileWrapper(FilePath base, FilePath file) throws IOException, InterruptedException {
        this(file.getName(),
                file.getRemote().substring(base.getRemote().length() + 1),
                file.isDirectory(),
                file.length(),
                file.lastModified());
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return directory;
    }

    public long getLength() {
        return length;
    }

    public long getLastModified() {
        return lastModified;
    }

    @Override
    public String toString() {
        return getPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileWrapper)) return false;

        FileWrapper that = (FileWrapper)o;

        return getPath().equals(that.getPath());

    }

    @Override
    public int hashCode() {
        return getPath().hashCode();
    }
}

package cereal;

import com.sun.jna.Pointer;

import java.io.Closeable;
import java.io.IOException;

public class Language implements Closeable {
    private static final LibCereal LIB = LibCereal.INSTANCE;

    final Pointer ptr;

    Language(final Pointer ptr) {  this.ptr = ptr;  }

    public Language(final String name) {  this.ptr = LIB.language_new(name);  }

    public void setName(final String name) {  LIB.language_set_name(this.ptr, name);  }

    public String getName() {  return LIB.language_get_name(this.ptr);  }

    public void destroy() {  LIB.language_destroy(this.ptr);  }

    @Override
    public void close() throws IOException {  this.destroy();   }

}

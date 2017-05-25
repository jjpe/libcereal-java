package cereal;

import com.sun.jna.Pointer;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Proxy convenience class to manipulate libamplify messages.
 */
public class Msg implements Closeable {
    private static final LibCereal LIB = LibCereal.INSTANCE;

    final Pointer ptr;

    public Msg() {  this.ptr = LIB.msg_new();  }

    public Msg setProcess(final String process) {
        LIB.msg_set_process(this.ptr, process);
        return this;
    }

    public String getProcess() {  return LIB.msg_get_process(this.ptr);  }

    public Msg setRequestNumber(long requestNumber) {
        LIB.msg_set_request_number(this.ptr, requestNumber);
        return this;
    }

    public long getRequestNumber() {  return LIB.msg_get_request_number(this.ptr);  }

    public Msg setKind(final String kind) {
        LIB.msg_set_kind(this.ptr, kind);
        return this;
    }

    public String getKind() {  return LIB.msg_get_kind(this.ptr);  }

    public Msg setOrigin(final String origin) {
        LIB.msg_set_origin(this.ptr, origin);
        return this;
    }

    public String getOrigin() {  return LIB.msg_get_origin(this.ptr);  }

    public Msg setContents(final Contents contents) {
        LIB.msg_set_contents(this.ptr, contents.ptr);
        return this;
    }

    public Contents getContents() {  return new Contents(LIB.msg_get_contents(this.ptr));  }

    public Msg addRegions(final Region... regions) {
        for (final Region region : regions) {
            LIB.msg_add_region(this.ptr, region.ptr);
        }
        return this;
    }

    public Msg clearRegions() {
        LIB.msg_clear_regions(this.ptr);
        return this;
    }

    public Region getRegion(final long index) {
        assert(index < this.countRegions());
        return new Region(LIB.msg_get_region(this.ptr, index));
    }

    public long countRegions() {  return LIB.msg_count_regions(this.ptr);  }

    public List<Region> getRegions() {
        final List<Region> regions = new ArrayList<>();
        final long numRegions = this.countRegions();
        for (long i = 0; i < numRegions; i++) {
            regions.add(this.getRegion(i));
        }
        return regions;
    }

    public Msg setLanguage(final Language language) {
        LIB.msg_set_language(this.ptr, language == null ? null : language.ptr);
        return this;
    }

    public Language getLanguage() {  return new Language(LIB.msg_get_language(this.ptr));  }

    public Msg setAst(final Ast ast) {
        LIB.msg_set_ast(this.ptr, ast.ptr);
        return this;
    }

    public Ast getAst() {
        final Pointer p = LIB.msg_get_ast(this.ptr);
        if (p == null) { return null; }
        return new Ast(p);
    }



    public void destroy() {  LIB.msg_destroy(this.ptr);  }

    @Override
    public void close() throws IOException {  this.destroy();   }

}

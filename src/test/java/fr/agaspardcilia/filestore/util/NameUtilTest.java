package fr.agaspardcilia.filestore.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameUtilTest {

    @Test
    void testFileName() {
        assertFalse(NameUtil.isFileNameValid(""));
        assertFalse(NameUtil.isFileNameValid(null));
        assertFalse(NameUtil.isFileNameValid("loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooog name"));
        assertFalse(NameUtil.isFileNameValid("/path/to/your/password"));
        assertFalse(NameUtil.isFileNameValid(""));
        assertFalse(NameUtil.isFileNameValid("-."));
        assertTrue(NameUtil.isFileNameValid("valid.txt"));
    }
}

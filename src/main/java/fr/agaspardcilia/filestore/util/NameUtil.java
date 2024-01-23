package fr.agaspardcilia.filestore.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Contains method related to naming.
 */
public class NameUtil {
    private static final String ALLOWED_CHARS_REGEX = "[\\.\\-_ ]+";

    private NameUtil() {
        // Do not instantiate.
    }

    /**
     * Tests if a given name is a valid file name. A valid name only contains alphanumeric or '.-_ ' characters  and is
     * less than 64 characters.
     *
     * @param name the name to test.
     * @return {@code true} if the name is valid, {@code false} otherwise.
     */
    public static boolean isFileNameValid(String name) {
        if (StringUtils.isEmpty(name) || name.length() > 64) {
            return false;
        }

        // Remove all allowed special chars, so we can test for alphanumeric only.
        String nameWithoutAllowed = name.replaceAll(ALLOWED_CHARS_REGEX, "");
        return StringUtils.isAlphanumeric(nameWithoutAllowed);
    }
}

package cs410;

public class Scrambler {
    public static void main(String[] args) {
        for (String s : args) {
            System.out.println(scramble(s, 0));
        }
    }

    /* No comments: the code is meant to by a mystery. */
    public static long scramble(String text, long init) {
        long h = init;
        for (char c : text.toCharArray()) {
            h ^= mix((long) c);
            h *= m;
        }
        return h;
    }

    static final long m = 0x880355f21e6d1965L;
    private static long mix(long h) {
        h ^= (h) >> 23;
        h *= 0x2127599bf4325c37L;
        h ^= (h >> 47);
        return h;
    }
}

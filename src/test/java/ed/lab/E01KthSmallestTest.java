package ed.lab;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class E01KthSmallestTest {

    @ParameterizedTest
    @CsvFileSource(resources = "E02.csv", useHeadersInDisplayName = true, delimiter = '|')
    void kthSmallest(String input, int k, int expected) {
        final TreeNode<Integer> root = BTUtils.parseBT(input, Integer::parseInt);

        E01KthSmallest test = new E01KthSmallest();
        int result = test.kthSmallest(root, k);

        assertEquals(expected, result,
                String.format("Se esperaba %d en lugar de %d", expected, result));
    }
}
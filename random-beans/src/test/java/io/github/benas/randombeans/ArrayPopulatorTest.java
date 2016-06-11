/**
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Randomizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayPopulatorTest {

    private static final int INT = 10;
    private static final char CHAR = 'R';
    private static final String STRING = "FOO";

    @Mock
    private PopulatorContext context;
    @Mock
    private EnhancedRandomImpl enhancedRandom;
    @Mock
    private RandomizerProvider randomizerProvider;
    @Mock
    private Randomizer integerRandomizer, characterRandomizer;

    private ArrayPopulator arrayPopulator;

    @Before
    public void setUp() {
        arrayPopulator = new ArrayPopulator(enhancedRandom, randomizerProvider);
        when(enhancedRandom.nextInt()).thenReturn(INT);
        when(randomizerProvider.getRandomizerByType(Integer.TYPE)).thenReturn(integerRandomizer);
        when(randomizerProvider.getRandomizerByType(Character.TYPE)).thenReturn(characterRandomizer);
        when(integerRandomizer.getRandomValue()).thenReturn(INT);
        when(characterRandomizer.getRandomValue()).thenReturn(CHAR);
        when(enhancedRandom.doPopulateBean(String.class, context)).thenReturn(STRING);
    }

    @Test
    public void getRandomArray() {
        Object array = arrayPopulator.getRandomArray(String[].class, context);

        assertThat(array).isInstanceOf(String[].class);
        String[] strings = (String[]) array;
        for (String string : strings) {
            assertThat(string).isEqualTo(STRING);
        }
    }

    @Test
    public void getRandomPrimitiveArray() {
        Object array = arrayPopulator.getRandomPrimitiveArray(Integer.TYPE);

        assertThat(array).isInstanceOf(int[].class);
        int[] ints = (int[]) array;
        for (int i : ints) {
            assertThat(i).isEqualTo(INT);
        }
    }

    @Test
    public void charArraysShouldBeFilledWithOnlyAlphabeticLetters() {
        char[] chars = (char[]) arrayPopulator.getRandomArray(char[].class, context);

        for (char c : chars) {
            assertThat(c).isEqualTo(CHAR);
        }
    }
}

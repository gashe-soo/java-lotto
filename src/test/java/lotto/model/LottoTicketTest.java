package lotto.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SuppressWarnings("NonAsciiCharacters")
public class LottoTicketTest {

    @Test
    void 숫자가_중복되면_오류_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> LottoTicket.of(Arrays.asList(1, 1, 2, 4, 5, 45))
        ).withMessageContaining("번호는 중복되면 안됩니다.");
    }

    @Test
    void 숫자가_6개보다_적으면_오류_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> LottoTicket.of(Arrays.asList(1, 2, 4, 5, 45))
        ).withMessageContaining("6개의 번호가 필요합니다.");
    }

    @Test
    void 숫자가_6개보다_많으면_오류_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> LottoTicket.of(Arrays.asList(1, 2, 4, 5, 6, 7, 45))
        ).withMessageContaining("6개의 번호가 필요합니다.");
    }

    @Test
    void 특정번호를_포함하는지_확인() {
        LottoTicket lottoTicket = LottoTicket.of(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertThat(lottoTicket.contains(1)).isTrue();
        assertThat(lottoTicket.contains(7)).isFalse();
    }

    @Test
    void 당첨_로또와_번호_비교확인() {
        LottoTicket winningTicket = LottoTicket.of(Arrays.asList(1, 2, 3, 4, 5, 6));
        LottoTicket lottoTicket = LottoTicket.of(Arrays.asList(3, 4, 5, 6, 7, 8));

        assertThat(lottoTicket.compareTicket(winningTicket)).isEqualTo(4);
    }

}

package lotto.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("NonAsciiCharacters")
class WinningIdentifierTest {

    private LottoTicket winningTicket;
    private List<LottoTicket> lottoTickets;
    private WinningIdentifier winningIdentifier;


    @BeforeEach
    void 설정() {
        winningTicket = new LottoTicket(Arrays.asList(1, 2, 3, 4, 5, 6));
        winningIdentifier = new WinningIdentifier(winningTicket, LottoNumber.of(7));
        lottoTickets = Arrays.asList(
                new LottoTicket(Arrays.asList(1, 2, 3, 4, 5, 7)),   // 2
                new LottoTicket(Arrays.asList(1, 2, 3, 4, 5, 8)),   // 3
                new LottoTicket(Arrays.asList(1, 2, 3, 4, 7, 9)),   // 4
                new LottoTicket(Arrays.asList(1, 2, 3, 4, 7, 8)),   // 4
                new LottoTicket(Arrays.asList(1, 2, 3, 7, 8, 9)),   // 5
                new LottoTicket(Arrays.asList(7, 8, 9, 10, 11, 12)) // MISS
        );
    }

    @Test
    void 로또_2개이하_일치_확인() {
        LottoTicket lottoTicketMatchTwo = new LottoTicket(Arrays.asList(1, 2, 7, 8, 9, 10));
        LottoTicket lottoTicketMatchOne = new LottoTicket(Arrays.asList(1, 11, 7, 8, 9, 10));
        LottoTicket lottoTicketMatchZero = new LottoTicket(Arrays.asList(12, 11, 7, 8, 9, 10));

        assertThat(winningIdentifier.checkWinning(lottoTicketMatchTwo)).isEqualTo(Rank.MISS);
        assertThat(winningIdentifier.checkWinning(lottoTicketMatchOne)).isEqualTo(Rank.MISS);
        assertThat(winningIdentifier.checkWinning(lottoTicketMatchZero)).isEqualTo(Rank.MISS);
    }

    @Test
    void 로또_3개_일치_확인() {
        LottoTicket lottoTicket = new LottoTicket(Arrays.asList(1, 2, 3, 7, 8, 9));
        assertThat(winningIdentifier.checkWinning(lottoTicket)).isEqualTo(Rank.FIFTH);
    }

    @Test
    void 로또_4개_일치_확인() {
        LottoTicket lottoTicket = new LottoTicket(Arrays.asList(1, 2, 3, 4, 8, 9));
        assertThat(winningIdentifier.checkWinning(lottoTicket)).isEqualTo(Rank.FOURTH);
    }

    @Test
    void 로또_5개_일치_3등_확인() {
        LottoTicket lottoTicket = new LottoTicket(Arrays.asList(1, 2, 3, 4, 5, 9));
        assertThat(winningIdentifier.checkWinning(lottoTicket)).isEqualTo(Rank.THIRD);
    }

    @Test
    void 로또_5개_일치_2등_확인() {
        LottoTicket lottoTicket = new LottoTicket(Arrays.asList(1, 2, 3, 4, 5, 7));
        assertThat(winningIdentifier.checkWinning(lottoTicket)).isEqualTo(Rank.SECOND);
    }

    @Test
    void 로또_6개_일치_확인() {
        LottoTicket lottoTicket = new LottoTicket(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertThat(winningIdentifier.checkWinning(lottoTicket)).isEqualTo(Rank.FIRST);
    }

    @Test
    void 여러개_로또당첨_결과_한번에_확인() {
        WinningReport report = winningIdentifier.checkTicketsWinning(lottoTickets);
        assertAll(
                () -> assertThat(report.getRankCount(Rank.FIRST)).isEqualTo(0),
                () -> assertThat(report.getRankCount(Rank.SECOND)).isEqualTo(1),
                () -> assertThat(report.getRankCount(Rank.THIRD)).isEqualTo(1),
                () -> assertThat(report.getRankCount(Rank.FOURTH)).isEqualTo(2),
                () -> assertThat(report.getRankCount(Rank.FIFTH)).isEqualTo(1),
                () -> assertThat(report.getRankCount(Rank.MISS)).isEqualTo(1)
        );
    }

    @Test
    void 로또당첨_1이하_수익률계산() {
        lottoTickets = Arrays.asList(
                new LottoTicket(Arrays.asList(7, 8, 9, 10, 11, 12)),
                new LottoTicket(Arrays.asList(7, 8, 9, 10, 11, 12)),
                new LottoTicket(Arrays.asList(7, 8, 9, 10, 11, 12)),
                new LottoTicket(Arrays.asList(7, 8, 9, 10, 11, 12)),
                new LottoTicket(Arrays.asList(7, 8, 9, 10, 11, 12)),
                new LottoTicket(Arrays.asList(1, 2, 3, 10, 11, 12))
        );

        WinningReport winningReport = winningIdentifier.checkTicketsWinning(lottoTickets);
        // 5등 하나 당첨. 5000/6000
        assertThat(winningReport.getProfitRate()).isEqualTo(0.83);
    }

    @Test
    void 로또당첨_1이상_수익률계산() {
        WinningReport winningReport = winningIdentifier.checkTicketsWinning(lottoTickets);

        // 2등, 3등, 4등*2, 5등 4,650_000원 / 6000원
        assertThat(winningReport.getProfitRate()).isEqualTo(5267.5);
    }


}
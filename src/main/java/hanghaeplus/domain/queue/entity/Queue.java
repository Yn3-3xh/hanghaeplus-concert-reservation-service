package hanghaeplus.domain.queue.entity;

import hanghaeplus.domain.common.AbstractAuditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "queue")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Queue extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long concertId;

    private int runningLimitedCount;

    public int calculateUpdatedRunningCount(int expiredCnt, int activatedCount) {
        int waitingToActivatedCount = expiredCnt;
        if (activatedCount < this.getRunningLimitedCount()) {
            waitingToActivatedCount += this.getRunningLimitedCount() - activatedCount;
        }
        return waitingToActivatedCount;
    }
}
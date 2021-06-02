package jpabook.jpashop.domain.service.query;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
public class OrderQueryService {
    public List<OrderDto> orderV3(){
        return new ArrayList<>();
    }
}

package top.myjnxj.lotteryexposer.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.myjnxj.lotteryexposer.po.Prize;
import top.myjnxj.lotteryexposer.po.PrizeExample;

public interface PrizeMapper {
    int countByExample(PrizeExample example);

    int deleteByExample(PrizeExample example);

    int insert(Prize record);

    int insertSelective(Prize record);

    List<Prize> selectByExample(PrizeExample example);

    int updateByExampleSelective(@Param("record") Prize record, @Param("example") PrizeExample example);

    int updateByExample(@Param("record") Prize record, @Param("example") PrizeExample example);
}
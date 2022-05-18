package com.sikina.mybatisproblem;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExampleMapper {
    @Select("""
        <script>
            SELECT
                id, name
            FROM
                example
            WHERE
                <if test="ids.stream().distinct().count() == 1">
                    id = #{ids[0]}
                </if>
                <if test="@java.util.Arrays@stream(ids.toArray()).distinct().count() > 1">
                    id IN\040
                    <foreach item='item' index='index' collection='@java.util.Arrays@stream(ids.toArray()).distinct().collect(@java.util.stream.Collectors@toList())' open='(' separator=',' close=')'> #{item} </foreach>
                </if>
        </script>
        """)
    public List<ExampleObject> getSomeThingsBreaks(List<Integer> ids);

    @Select("""
        <script>
            SELECT
                id, name
            FROM
                example
            WHERE
                id IN\040
                <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'> #{item} </foreach>
        </script>
        """)
    public List<ExampleObject> getSomeThingsWorks(List<Integer> ids);
}

package com.guang.homework.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author guangyong.deng
 * @date 2021-12-02 17:20
 */
public class Question2 {

    private static List<DeptDTO> list = new ArrayList<>();

    static {
        list.add(new DeptDTO("1", ""));
        list.add(new DeptDTO("1", ""));
        list.add(new DeptDTO("2", "1"));
        list.add(new DeptDTO("3", "1"));
        list.add(new DeptDTO("4", "2"));
        list.add(new DeptDTO("5", "2"));
        list.add(new DeptDTO("6", "3"));
        list.add(new DeptDTO("7", ""));
        list.add(new DeptDTO("8", "10"));
    }

    public static void main(String[] args) {
        List<DeptDTO> deptDTOS = new ArrayList<>();

        // 寻找一级部门，及父部门不存在
        // 1.第一种情况 pid 为空
        // 2.第二种情况 pid 的数字在 list 中不存在
         deptDTOS.stream().flatMap(
                 deptDTO -> {
            return Stream.of(deptDTO.getPid());
        }).collect(Collectors.toList());
    }

   static class DeptDTO {

        private String id;

        private String pid;

        public DeptDTO(String id, String pid) {
            this.id = id;
            this.pid = pid;
        }

        public String getId() {
            return id;
        }

        public DeptDTO setId(String id) {
            this.id = id;
            return this;
        }

        public String getPid() {
            return pid;
        }

        public DeptDTO setPid(String pid) {
            this.pid = pid;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DeptDTO deptDTO = (DeptDTO) o;
            return Objects.equals(id, deptDTO.id) &&
                    Objects.equals(pid, deptDTO.pid);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, pid);
        }
    }
}

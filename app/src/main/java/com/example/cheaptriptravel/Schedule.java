package com.example.cheaptriptravel;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 6/3/2018.
 */

public class Schedule extends DataSupport {


        private int id;

        private String event_Name;

        private double start_time;

        private double end_time;
        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getEvent_Name() {
                return event_Name;
        }

        public void setEvent_Name(String event_Name) {
                this.event_Name = event_Name;
        }

        public double getStart_time() {
                return start_time;
        }

        public void setStart_time(double start_time) {
                this.start_time = start_time;
        }

        public double getEnd_time() {
                return end_time;
        }

        public void setEnd_time(double end_time) {
                this.end_time = end_time;
        }


}

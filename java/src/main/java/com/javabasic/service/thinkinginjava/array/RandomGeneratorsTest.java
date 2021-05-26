package com.javabasic.service.thinkinginjava.array;

import com.javabasic.dao.Generator;
import com.javabasic.service.thinkinginjava.array.CreateData.CtDate;
import com.javabasic.service.thinkinginjava.array.CreateData.Storage;
import com.javabasic.service.thinkinginjava.array.CreateData.Users;
import com.javabasic.service.thinkinginjava.io.Logs;
import java.sql.SQLException;
import java.util.Random;

public class RandomGeneratorsTest {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, SQLException {
        Logs.getLogs( "RandomGeneratorsTest", true );
//        GeneratorsTest.test( RandomGenerator.class );

        Class<? extends Generator>[] a = new Class[]{RandomGenerator.Integer.class
                , Users.Name.class
                , Users.sex.class
                , Users.IdentityCard.class};

        Storage storage = new Storage();
//        storage.Dbms( "mysql", "demo1", a, "users" );

        Class<? extends Generator>[] b = new Class[]{
                RandomGenerator.Integer.class     //apply_id
                , RandomGenerator.Integer.class   //user_id
                , Users.IdentityCard.class        //id_number,IdentityCard  身份证
                , Users.PhoneNum.class            //phone_number
                , Users.BankCard.class            //credit_card_number
                , RandomGenerator.Boolean.class   //Audit_results , status , audit_status
                , RandomGenerator.Float.class    //limits
                , RandomGenerator.Integer.class   //borrow_id
                , RandomGenerator.Double.class    //total_Amount
                , RandomGenerator.Float.class    //used_credit_line
                , Users.Name.class                //name
                , Users.sex.class                 //sex
                , RandomGenerator.Double.class    //applied_amount
                , RandomGenerator.Boolean.class   //audit_status
                , RandomGenerator.Float.class    //interest
                , RandomGenerator.Float.class    //service_charge
                , RandomGenerator.Float.class    //interest_penalty
                , RandomGenerator.Integer.class    //repayment_id
                , RandomGenerator.Integer.class    //sequence_number
                , RandomGenerator.TimeStampe.class    //ori_rep_time
                , RandomGenerator.Double.class    //ori_rep_amount
                , RandomGenerator.Float.class    //ori_rep_rate
                , RandomGenerator.Double.class    //rep_amount
                , RandomGenerator.Float.class    //rep_rate
                , RandomGenerator.TimeStampe.class    //rep_time
                , RandomGenerator.Float.class    //payment
        };

        Random r = new Random();
        for (int i = 1; i <= r.nextInt( 10000 ); i++) {
            CtDate ct = new CtDate( b );
            System.out.println( ct.getDateStr() );
        }

//        System.out.println();
    }
}

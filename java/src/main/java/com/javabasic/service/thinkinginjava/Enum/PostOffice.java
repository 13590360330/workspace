package com.javabasic.service.thinkinginjava.Enum;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.util.Iterator;

/**
 * TODO [职责链设计模式 P606,类中的枚举]
 *
 * 程序员以多种不同的方式来解决一个问题,然后将它们链接在一起,当一个请求到来时,它遍历这个链,直到链中的某个方案能够处理该请求
 */

class Mail {
    enum GeneralDelivery {YES, NO1, NO2, NO3, NO4, NO5}

    enum Scannability {UNSCANNABLE, YES1, YES2, YES3, YES4}

    enum Readability {ILLEGIBLE, YES1, YES2, YES3, YES4}

    enum Address {INCORRECT, OK1, OK2, OK3, OK4, OK5, OK6}

    enum ReturnAddress {MISSING, OK1, OK2, OK3, OK4, OK5}

    GeneralDelivery generalDelivery;
    Scannability scannability;
    Readability readability;
    Address address;
    ReturnAddress returnAddress;
    static long counter = 0;
    long id = counter++;

    public String toString() {
        return "Mail " + id;
    }

    public String details() {
        return toString() +
                ", General Delivery: " + generalDelivery +
                ", Address Scanability: " + scannability +
                ", Address Readability: " + readability +
                ", Address Address: " + address +
                ", Address ReturnAddress: " + returnAddress;
    }

    public static Mail randomMail() {
        Mail m = new Mail();
        m.generalDelivery = Enums.random( GeneralDelivery.class );
        m.scannability = Enums.random( Scannability.class );
        m.readability = Enums.random( Readability.class );
        m.address = Enums.random( Address.class );
        m.returnAddress = Enums.random( ReturnAddress.class );
        return m;
    }

    public static Iterable<Mail> generator(final int count) {
        return new Iterable<Mail>() {
            int n = count;

            @Override
            public Iterator<Mail> iterator() {
                return new Iterator<Mail>() {
                    @Override
                    public boolean hasNext() {
                        return n-- > 0;
                    }

                    @Override
                    public Mail next() {
                        return randomMail();
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}

/**
 * 邮件分发 原理 P609
 */
public class PostOffice {
    /**
     * 职责链由enum MailHandler实现
     */
    enum MailHandler {
        GENERAL_DELIVERY {
            boolean handle(Mail m) {
                switch (m.generalDelivery) {
                    case YES:
                        System.out.println( "Using general delivery for " + m );
                        return true;
                    default:
                        return false;
                }
            }
        },
        MACHINE_SCAN {
            boolean handle(Mail m) {
                switch (m.scannability) {
                    case UNSCANNABLE:
                        return false;
                    default:
                        switch (m.address) {
                            case INCORRECT:
                                return false;
                            default:
                                System.out.println( "Delivering " + m + " automatically" );
                                return true;
                        }
                }
            }
        },
        VISUAL_INSPECTION {
            boolean handle(Mail m) {
                switch (m.readability) {
                    case ILLEGIBLE:
                        return false;
                    default:
                        switch (m.address) {
                            case INCORRECT:
                                return false;
                            default:
                                System.out.println( "Delivering " + m + " normally" );
                                return true;
                        }
                }
            }
        },
        RETURN_TO_SENDER {
            boolean handle(Mail m) {
                switch (m.returnAddress) {
                    case MISSING:
                        return false;
                    default:
                        System.out.println( "Returning " + m + " to sender" );
                        return true;
                }
            }
        };

        abstract boolean handle(Mail m);
    }

    static void handle(Mail m) {
        for (MailHandler handler : MailHandler.values())
            if (handler.handle( m ))
                return;
        System.out.println( m + " is a dead letter" );
    }

    public static void main(String[] args) {
        Logs.getLogs( "PostOffice" );
        for (Mail mail : Mail.generator( 10 )) {
            System.out.println( mail.details() );
            handle( mail );
            System.out.println( "******" );
        }
    }

}

FROM centos:6

RUN yum install boost-devel libuuid-devel pkgconfig gcc-c++ make autoconf automake ruby libtool help2man doxygen graphviz -y

RUN yum install openais-devel cman-devel -y

RUN yum install cyrus-sasl-devel nss-devel nspr-devel -y

RUN yum install xqilla-devel xerces-c-devel -y

RUN yum install ruby ruby-devel swig -y

WORKDIR /opt

RUN curl http://archive.apache.org/dist/qpid/0.20/qpid-cpp-0.20.tar.gz | tar xvzf -

RUN cd /opt/qpidc-0.20 && ./configure --prefix=/opt/qpid

RUN yum install patch -y

ADD sslt.patch ./

RUN patch /usr/include/nss3/sslt.h sslt.patch

RUN cd qpidc-0.20/ && make all && make install

CMD /opt/qpid/sbin/qpidd --auth no

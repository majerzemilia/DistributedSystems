//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.3
//
// <auto-generated>
//
// Generated from file `smarthouse.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package SmartHouse;

/**
 * Helper class for marshaling/unmarshaling AvailableTeaTypes.
 **/
public final class AvailableTeaTypesHelper
{
    public static void write(com.zeroc.Ice.OutputStream ostr, TeaType[] v)
    {
        if(v == null)
        {
            ostr.writeSize(0);
        }
        else
        {
            ostr.writeSize(v.length);
            for(int i0 = 0; i0 < v.length; i0++)
            {
                TeaType.ice_write(ostr, v[i0]);
            }
        }
    }

    public static TeaType[] read(com.zeroc.Ice.InputStream istr)
    {
        final TeaType[] v;
        final int len0 = istr.readAndCheckSeqSize(1);
        v = new TeaType[len0];
        for(int i0 = 0; i0 < len0; i0++)
        {
            v[i0] = TeaType.ice_read(istr);
        }
        return v;
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<TeaType[]> v)
    {
        if(v != null && v.isPresent())
        {
            write(ostr, tag, v.get());
        }
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, TeaType[] v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            AvailableTeaTypesHelper.write(ostr, v);
            ostr.endSize(pos);
        }
    }

    public static java.util.Optional<TeaType[]> read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            TeaType[] v;
            v = AvailableTeaTypesHelper.read(istr);
            return java.util.Optional.of(v);
        }
        else
        {
            return java.util.Optional.empty();
        }
    }
}

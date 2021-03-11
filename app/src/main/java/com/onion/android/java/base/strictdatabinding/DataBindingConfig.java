package com.onion.android.java.base.strictdatabinding;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

/* DataBinding 配置 */
public class DataBindingConfig {

    private final int layout;

    private final int vmVariableId;

    private final ViewModel stateViewModel;

    /*
    * SparseArray将整数映射到对象，并且与普通的对象数组不同，它的索引可以包含空格。
    * SparseArray旨在比HashMap更高的内存效率，因为它避免了自动装箱键，并且其数据结构不依赖于每个映射的额外入口对象。
    * 请注意，此容器使用二进制搜索来查找键，从而将其映射保留在数组数据结构中。 该实现不适用于可能包含大量项目的数据结构。 它通常比HashMap慢，因为查找需要二进制搜索，添加和删除需要在数组中插入和删除条目。 对于容纳多达数百个物品的容器，性能差异小于50％。
    * 为了提高性能，该容器在删除键时进行了优化：与其立即压缩数组，不如将其压缩为删除。 然后可以将该条目重新用于同一密钥，或者稍后在所有已删除条目的单个垃圾回收中进行压缩。 每当需要增长数组或检索映射大小或条目值时，都必须执行此垃圾回收。
    * 可以使用keyAt(int)和valueAt(int)遍历此容器中的项目。 使用keyAt(int)和索引的升序对键进行迭代keyAt(int)升序返回键。 在valueAt(int)的情况下，与键对应的值以升序返回。*/
    private SparseArray bindingParams = new SparseArray();

    public DataBindingConfig(int layout, int vmVariableId, ViewModel stateViewModel) {
        this.layout = layout;
        this.vmVariableId = vmVariableId;
        this.stateViewModel = stateViewModel;
    }

    public int getLayout() {
        return layout;
    }

    public int getVmVariableId() {
        return vmVariableId;
    }

    public ViewModel getStateViewModel() {
        return stateViewModel;
    }

    public SparseArray getBindingParams() {
        return bindingParams;
    }

    public DataBindingConfig addBindingParams(
           @NonNull Integer variableId,
           @NonNull Object object
    ){
        if(bindingParams.get(variableId) == null){
            bindingParams.put(variableId,object);
        }

        return this;
    }
}

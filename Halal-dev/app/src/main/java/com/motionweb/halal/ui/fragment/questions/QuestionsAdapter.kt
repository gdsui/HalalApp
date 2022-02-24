package com.motionweb.halal.ui.fragment.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.motionweb.halal.data.network.questions.Question
import com.motionweb.halal.databinding.ItemQuestionAnswerBinding

class QuestionsAdapter : RecyclerView.Adapter<QuestionAnswerVH>() {

    var dataSource: List<Question> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAnswerVH =
        QuestionAnswerVH.create(parent)

    override fun onBindViewHolder(holder: QuestionAnswerVH, position: Int) {
        holder.bind(dataSource[position])
    }

    override fun getItemCount() = dataSource.size
}

class QuestionAnswerVH(private val view: ItemQuestionAnswerBinding) :
    RecyclerView.ViewHolder(view.root) {

    fun bind(question: Question) {
        with(view) {
            tvAuthorName.text = question.author
            tvQuestion.text = question.question
        }
        if (question.answer == null) {
            view.tvAnswerLabel.isVisible = false
            view.tvAnswer.isVisible = false
        } else {
            view.tvAnswer.text = question.answer
        }
    }

    companion object {
        fun create(parent: ViewGroup): QuestionAnswerVH {
            val view = ItemQuestionAnswerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return QuestionAnswerVH(view)
        }
    }

}
